/*
 * @(#)Direct-X-Buffer.java	1.50 05/11/17
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileChannelImpl;


class DirectFloatBufferRU



    extends DirectFloatBufferU

    implements DirectBuffer
{






















































































































    // For duplicates and slices
    //
    DirectFloatBufferRU(DirectBuffer db,	        // package-private
			       int mark, int pos, int lim, int cap,
			       int off)
    {








	super(db, mark, pos, lim, cap, off);

    }

    public FloatBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 2);
        assert (off >= 0);
	return new DirectFloatBufferRU(this, -1, 0, rem, rem, off);
    }

    public FloatBuffer duplicate() {
	return new DirectFloatBufferRU(this,
					      this.markValue(),
					      this.position(),
					      this.limit(),
					      this.capacity(),
					      0);
    }

    public FloatBuffer asReadOnlyBuffer() {








	return duplicate();

    }


















































    public FloatBuffer put(float x) {




	throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(int i, float x) {




	throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(FloatBuffer src) {




































	throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(float[] src, int offset, int length) {






















	throw new ReadOnlyBufferException();

    }
    
    public FloatBuffer compact() {











	throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
	return true;
    }

    public boolean isReadOnly() {
	return true;
    }













































    public ByteOrder order() {





	return ((ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN)
		? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

    }


























}
